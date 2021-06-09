from utils import loadDataMoreInputs, normalisation

# problem hapiness = w0 + w1 * GDPcapita + w2 * freedom
import os
crtDir =  os.getcwd()
filePath = os.path.join(crtDir, 'world-happiness-report-2017.csv')
inputs, outputs = loadDataMoreInputs(filePath, ['Economy..GDP.per.Capita.', 'Freedom'], 'Happiness.Score')

feature1 = [ex[0] for ex in inputs]     #Economy..GDP.per.Capita.
feature2 = [ex[1] for ex in inputs]     #Freedom   

#train and test samples normalised
import numpy as np 
np.random.seed()
indexes = [i for i in range(len(inputs))]
trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
testSample = [i for i in indexes  if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]
testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]


trainInputs, testInputs = normalisation(trainInputs, testInputs)
trainOutputs, testOutputs = normalisation(trainOutputs, testOutputs)

feature1train = [ex[0] for ex in trainInputs]
feature2train = [ex[1] for ex in trainInputs]

feature1test = [ex[0] for ex in testInputs]
feature2test = [ex[1] for ex in testInputs]


#using sklearn tool: 
from sklearn import linear_model
from sklearn.metrics import mean_squared_error
regressor = linear_model.SGDRegressor(alpha = 0.01, max_iter = 1000,average=True)
regressor.fit(trainInputs,trainOutputs)
w0, w1, w2 = regressor.intercept_[0], regressor.coef_[0], regressor.coef_[1]
print('the learnt model: f(x1,x2) = ', w0, ' + ', w1, ' * x1','+', w2 , '*x2')
#predictions for testInputs with sklearn tool:
computedTestOutputs=regressor.predict(testInputs)
print('Predictions by tool):\n',computedTestOutputs)
mse=mean_squared_error(testOutputs,computedTestOutputs)
print("prediction error (tool): ", mse)

'''
#using my Regressor 
from MyBGDRegression import *
regressor = MyBGDRegression()
regressor.fit(trainInputs, trainOutputs)
w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
print('the learnt model: f(x1,x2) = ', w0, ' + ', w1, ' * x1','+', w2 , '*x2')
#predictions for testInputs with my code:
computedTestOutputs=regressor.predict(testInputs)
print('Predictions using manual BGD regressor for testInputs):\n',computedTestOutputs)
from predErr import rootMeanSquareError

rmse=rootMeanSquareError(testOutputs,computedTestOutputs)
print("prediction error (manual): ", rmse)
#'''