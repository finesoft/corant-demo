package org.corant.demo.ddd.infrastructure;

import static org.corant.shared.util.Assertions.shouldBeTrue;
import static org.corant.shared.util.Streams.copy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageFormatRuntimeException;
import javax.jms.TextMessage;
import org.corant.shared.exception.CorantRuntimeException;
import org.corant.shared.util.Resources.InputStreamResource;
import org.corant.suites.bundle.GlobalMessageCodes;
import org.corant.suites.bundle.exception.GeneralRuntimeException;
import org.corant.suites.jms.shared.annotation.MessageSend.SerializationSchema;
import org.corant.suites.jms.shared.annotation.MessageSerialization;
import org.corant.suites.jms.shared.context.MessageSerializer;
import org.corant.suites.json.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * corant-suites-ddd
 *
 * @author bingo 下午3:43:14
 *
 */
public class MessageSerializers {

  static final ObjectMapper jsonObjectMapper =
      JsonUtils.copyMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  @ApplicationScoped
  @MessageSerialization(schema = SerializationSchema.BINARY)
  public static class BinaryMessageSerializer implements MessageSerializer {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Message message, Class<T> clazz) {
      shouldBeTrue(message instanceof BytesMessage);
      shouldBeTrue(InputStreamResource.class.isAssignableFrom(clazz));
      BytesMessage bmsg = (BytesMessage) message;
      try {
        byte[] data = new byte[(int) bmsg.getBodyLength()];
        bmsg.readBytes(data);
        return (T) new InputStreamResource(new ByteArrayInputStream(data), null);
      } catch (JMSException e) {
        throw new CorantRuntimeException(e);
      }
    }

    @Override
    public Message serialize(JMSContext jmsContext, Serializable object) {
      shouldBeTrue(object instanceof InputStream);
      BytesMessage message = jmsContext.createBytesMessage();
      try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
        copy((InputStream) object, buffer);
        byte[] bytes = buffer.toByteArray();
        message.writeBytes(bytes);
        resolveSchemaProperty(message, SerializationSchema.BINARY);
        return message;
      } catch (JMSException | IOException e) {
        throw new MessageFormatRuntimeException(e.getMessage());
      }
    }
  }

  @ApplicationScoped
  @MessageSerialization(schema = SerializationSchema.JSON_STRING)
  public static class JsonMessageSerializer implements MessageSerializer {

    @Override
    public <T> T deserialize(Message message, Class<T> clazz) {
      shouldBeTrue(message instanceof TextMessage);
      TextMessage tMsg = (TextMessage) message;
      try {
        return from(tMsg.getText(), clazz);
      } catch (JMSException e) {
        throw new CorantRuntimeException(e);
      }
    }

    @Override
    public Message serialize(JMSContext jmsContext, Serializable object) {
      Message message = jmsContext.createTextMessage(to(object));
      resolveSchemaProperty(message, SerializationSchema.JSON_STRING);
      return message;
    }

    <T> T from(String text, Class<T> clazz) {
      try {
        return jsonObjectMapper.readValue(text, clazz);
      } catch (IOException e) {
        throw new GeneralRuntimeException(e, GlobalMessageCodes.ERR_OBJ_SEL, text, clazz.getName());
      }
    }

    String to(Serializable message) {
      try {
        return jsonObjectMapper.writeValueAsString(message);
      } catch (JsonProcessingException e) {
        throw new GeneralRuntimeException(e, GlobalMessageCodes.ERR_OBJ_SEL, message);
      }
    }
  }
}
