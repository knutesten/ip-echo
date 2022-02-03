FROM clojure:openjdk-17-slim-buster
RUN mkdir -p /opt/app
WORKDIR /opt/app
COPY . /opt/app
ENTRYPOINT clj -M:run --use-x-forwarded-for
