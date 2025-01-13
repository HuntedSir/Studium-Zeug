.global main

main:

push {lr}

mov r0, #12
push {r0}

mov r0, #13
push {r0}

mov r0, #14
push {r0}

mov r0, #15
push {r0}

bl diffofsums

/* Exit programm after linking back */
pop {lr}
bx lr

diffofsums:

pop {r0}
pop {r1}
pop {r2}
pop {r3}
add r0, r0, r1
add r1, r2, r3
sub r0, r0, r1
bx lr