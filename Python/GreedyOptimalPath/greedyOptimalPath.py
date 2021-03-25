myDict={}
n=0
stNode=0
destNode=0
file=open("hard_01_tsp.txt","r")
writefile=open("write.txt","w")
def data_reader():
    global myDict
    global n
    global stNode
    global destNode
    i=1
    sn=file.readline().split()
    n=int(sn[0].strip())
    while i<=n:
        elems=file.readline().split(",")
        lst=[]
        j=1
        while j<=n:
            lst.append(int(elems[j-1].strip()))
            j+=1
        myDict[i]=lst
        i+=1

    stNode=int(file.readline().split()[0].strip())
    destNode=int(file.readline().split()[0].strip())
    print("Datele de intrare sunt:")
    print(stNode)
    print(destNode)
    for key in myDict:
        print(myDict[key])
        
def display1(path,valueOfOptimalPath):
    print()
    print(len(path))
    print(path)
    print(valueOfOptimalPath)
    global n
    writefile.write(str(len(path)))
    writefile.write("\n")
    i=0
    while i<(len(path)-1):
        writefile.write(str(path[i]))
        writefile.write(",")
        i+=1
    writefile.write(str(path[i]))
    writefile.write("\n")
    writefile.write(str(valueOfOptimalPath))
    writefile.write("\n")
    
def next_minimum_unvisitedNode(currentNode, visited, myDict, n):
    minimum=999999
    foundNode=-1
    i=0
    for val in myDict[currentNode]:
        i+=1
        if val<minimum and visited[i]==False:
            foundNode=i
            minimum=val
    return foundNode
        

def parcurgere(stNode,destNode):
    global myDict
    global n
    visited=[False]*(n+1)
    visited[stNode]=True

    currentNode=stNode
    path=[stNode]
    valueOfOptimalPath=0
    while True:
        foundNode=next_minimum_unvisitedNode(currentNode,visited,myDict,n)
        if foundNode==-1 and stNode==destNode:
            valueOfOptimalPath+=myDict[currentNode][destNode-1]
            display1(path,valueOfOptimalPath)
            break
        elif foundNode==destNode:
            valueOfOptimalPath+=myDict[currentNode][foundNode-1]
            path.append(foundNode)
            visited[foundNode]=True
            display1(path,valueOfOptimalPath)
            break
        else:
            valueOfOptimalPath+=myDict[currentNode][foundNode-1]
            path.append(foundNode)
            visited[foundNode]=True
            currentNode=foundNode
        

def run():
    myDict={}
    n=0
    stNode=0
    destNode=0
    data_reader()
    print()
    parcurgere(1,1)
    parcurgere(2,4)
    writefile.close()
run()
