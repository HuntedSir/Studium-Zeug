.data
string:
        .asciz "%d\n"

.global main
.extern printf


main:
push {lr}
mvn r1, #420 
mov r2, #42
mul r0, r1, r2
mov r1, #1337
mov r2, #69
and r1, r1, r2
eor r0, r0, r1
mov r1, #11
mov r2, #41
orr r1, r1, r2
sub r0, r0, r1

mov r1, r0
ldr r0, =string
bl printf
pop {lr}
bx lr
