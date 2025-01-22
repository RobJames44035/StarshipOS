#  StarshipOS Copyright (c) 2025. R.A. James

from paho.mqtt import publish, subscribe

publish.single(
        'buildroot/test',
        payload="Hello, World!",
        qos=2,
        retain=True,
        hostname="localhost",
        port=1883)

message = subscribe.simple('buildroot/test')
print(message.payload.decode())
