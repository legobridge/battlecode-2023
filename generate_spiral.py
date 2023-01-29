i = 1
j = 4
isign = 1
jsign = 1
for x in range(60):
    print(f"new TranslatePair({i * isign}, {j * jsign}),")
    if x % 2 == 0:
        isign *= -1
        i += 2
    if x % 2 == 1:
        jsign *= -1
        j += 2
