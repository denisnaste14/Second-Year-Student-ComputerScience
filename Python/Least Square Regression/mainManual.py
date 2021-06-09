from utils import loadDataMoreInputs
from myRegression import MyLinearUnivariateRegression

import os
crtDir =  os.getcwd()
filePath = os.path.join(crtDir, 'world-happiness-report-2017.csv')
inputs, outputs = loadDataMoreInputs(filePath, ['Economy..GDP.per.Capita.', 'Freedom'], 'Happiness.Score')

import numpy as np 

np.random.seed()
indexes = [i for i in range(len(inputs))]
trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
validationSample = [i for i in indexes  if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]

validationInputs = [inputs[i] for i in validationSample]
validationOutputs = [outputs[i] for i in validationSample]

myR = MyLinearUnivariateRegression()
print(myR.fit(trainInputs,trainOutputs))