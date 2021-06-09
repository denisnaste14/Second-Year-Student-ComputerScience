from numpy import zeros
def my_confusion_matrix(v1,v2):
    ct=0
    d={}
    rev_d={}
    for i in range(0,len(v1)):
        if(v1[i] not in rev_d):
            rev_d.update({v1[i]:ct})
            d.update({ct:v1[i]})
            ct+=1
    confM=zeros((len(d),len(d)))
    for j in range(0,len(v1)):
        confM[rev_d[v1[j]]][rev_d[v2[j]]]+=1
    return confM

def my_accuracy(nrTotal,confM):
    TP=0
    for i in range(0,len(confM)):
        TP+=confM[i][i]
    return float(TP/nrTotal)

def my_average_precision(confM):
    #precision for 1 class :  prec=TP/(TP+FP)
    avgPrecision=0.0
    for i in range(0,len(confM)):
        TP=confM[i][i]
        FP=0
        for j in range(0,len(confM)):
            if(i != j):
                FP+=confM[j][i]
        avgPrecision+=float(TP/(TP+FP))
    avgPrecision/=len(confM)
    return avgPrecision

def my_average_recall(confM):
    #recall for 1 class : recall=TP/(TP+FN)
    avgRecall=0.0
    for i in range(0,len(confM)):
        TP=confM[i][i]
        FN=0
        for j in range(0,len(confM)):
            if(i != j):
                FN+=confM[i][j]
        avgRecall+=float(TP/(TP+FN))
    avgRecall/=len(confM)
    return avgRecall  