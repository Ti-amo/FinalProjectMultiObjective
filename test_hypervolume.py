from pygmo import hypervolume
'hypervolume' in dir()

f = open("test.txt", "r")

hypervolumeArray = []

while True:
    line = f.readline()
    tempstr = []
    while line.startswith("P") == True:
        mystr = line.split()
        for arr in mystr:
            if arr.replace('.', '', 1).isdigit():
                tempstr.append(arr)
        hypervolumeArray.append(tempstr)
        line = f.readline()
        tempstr = []
    if hypervolumeArray != []:
        hv = hypervolume(hypervolumeArray)
        ref_point = [120, 1, 2]
        print(hv.compute(ref_point))
    if ((line.startswith("T") == True) or (line.startswith("-") == True)):
        print("Aaaaaaaaaa")
        hypervolumeArray = []
    if ("" == line):
        print("file finished")
        break
