filepath = '/Users/agiri/Downloads/phaBalignment.txt'
with open(filepath) as file:
    line = file.readline()
    bacteria = {}
    while line != '':

        for char in ['*', '.', ':']:
            if char in line:
                line = file.readline()
                continue

        try:
            print(line.split()[1])
            name = line.split()[0]
            seq = line.split()[1]
            bacteria.setdefault(name, "")
            bacteria[name]+=(seq)
        except IndexError as e:8
            pass

        line = file.readline()
with open(filepath) as file:
    file = open("phaB.txt", "w")
    for k, v in bacteria.items():
        file.write(">" + v)
        file.write("\n")
