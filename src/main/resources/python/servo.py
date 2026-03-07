from gpiozero import AngularServo
from time import sleep

servo = AngularServo(
    18,
    min_angle=0,
    max_angle=180,
    min_pulse_width=0.0005,
    max_pulse_width=0.0025
)

try:
    while True:
        for angle in range(0, 181, 10):
            servo.angle = angle
            sleep(0.05)

        for angle in range(180, -1, -10):
            servo.angle = angle
            sleep(0.05)
finally:
    servo.detach()