from utils import loadData

#problem hapiness = w0 + w1 * GDPcapita
import os
crtDir =  os.getcwd()
filePath = os.path.join(crtDir, 'world-happiness-report-2017.csv')
inputs, outputs = loadData(filePath, 'Economy..GDP.per.Capita.', 'Happiness.Score')

#train and test samples 
import numpy as np 
np.random.seed()
indexes = [i for i in range(len(inputs))]
trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
testSample = [i for i in indexes  if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]

testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]

#train inputs have to be matrix
xx = [[el] for el in trainInputs]


# using sklearn tool 
from sklearn import linear_model
from sklearn.metrics import mean_squared_error
# model initialisation
regressor = linear_model.SGDRegressor(alpha = 0.01, max_iter = 1000,average=True) #average True - batch GD
# training the model by using the training inputs and known training outputs
regressor.fit(xx, trainOutputs)
# save the model parameters
w0, w1 = regressor.intercept_[0], regressor.coef_[0]
print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')

# makes predictions for test data (by tool)
computedTestOutputs = regressor.predict([[x] for x in testInputs])
print("Predictions using tool BGD regressor for testInputs:\n",computedTestOutputs)
#prediction error MSE with tool 
error = mean_squared_error(testOutputs, computedTestOutputs)
print("prediction error (tool): ", error)
#'''

'''
#cod propriu
from MyBGDRegression import *
from predErr import rootMeanSquareError

# model initialisation
regressor1 = MyBGDRegression()
# training the model by using the training inputs and known training outputs
regressor1.fit(xx, trainOutputs)
# save the model parameters
w0, w1 = regressor1.intercept_, regressor1.coef_[0]
print('the learnt model: f(x) = ', w0, ' + ', w1, ' * x')
# makes predictions for test data (manual)
computedTestOutputs = regressor1.predict([[x] for x in testInputs])
print('Predictions using manual BGD regressor for testInputs:\n',computedTestOutputs)
rmse=rootMeanSquareError(testOutputs,computedTestOutputs)
print("prediction error (manual): ", rmse)
 #'''
