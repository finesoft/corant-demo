FROM openjdk:8
ADD corant-demo-ddd-0.0.1-SNAPSHOT-bin-dist.zip /
EXPOSE 80
CMD unzip -o corant-demo-ddd-0.0.1-SNAPSHOT-bin-dist.zip -d /var/corant-demo-ddd/ && cd /var/corant-demo-ddd/bin/ && chmod +x run.sh && bash run.sh