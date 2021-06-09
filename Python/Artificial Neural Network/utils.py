#MAE
def meanAbsoluteError(output1,output2):
    mae=0.0
    for i in range(0,len(output1)):
        mae+=abs(output1[i]-output2[i])
    mae/=len(output1)
    return mae
    
def index_max_term(values):
    index=0
    for i in range(1,len(values)):
        if(values[i]>values[index]):
            index=i
    return index