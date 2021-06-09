from math import sqrt
#RMSE
def  rootMeanSquareError(output1,output2):
    rmse=0.0
    for i in range(0,len(output1)):
        rmse+=(output1[i]-output2[i])**2
    rmse/=len(output1)
    rmse=sqrt(rmse)
    return rmse

#MAE
def meanAbsoluteError(output1,output2):
    mae=0.0
    for i in range(0,len(output1)):
        mae+=abs(output1[i]-output2[i])
    mae/=len(output1)
    return mae









def averageMSE(mat1,mat2):
    totalError=0.0
    for i in range(0,len(mat1)):
        totalError+=rootMeanSquareError(mat1[i],mat2[i])
    return totalError/len(mat1)
