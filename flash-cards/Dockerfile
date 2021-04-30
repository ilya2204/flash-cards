FROM adoptopenjdk/openjdk15
COPY . /flash-cards/
WORKDIR flash-cards
RUN chmod +x entrypoint.sh
RUN ./gradlew --no-daemon build
ENTRYPOINT ["/flash-cards/entrypoint.sh"]
