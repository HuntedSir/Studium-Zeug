mov r1, #5
mov r2, #12
eor r1, r1, r2
eor r2, r2, r1
eor r1, r1, r2
mov r0, r1
bx lr