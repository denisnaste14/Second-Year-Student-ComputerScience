from sklearn import utils
from sklearn.datasets import load_iris

#data loading 
data = load_iris()
inputs = data['data']
outputs = data['target']

outputNames = data['target_names']
featureNames = list(data['feature_names'])

feature1 = [feat[featureNames.index('sepal length (cm)')] for feat in inputs]
feature2 = [feat[featureNames.index('sepal width (cm)')] for feat in inputs]
feature3 = [feat[featureNames.index('petal length (cm)')] for feat in inputs]
feature4 = [feat[featureNames.index('petal width (cm)')] for feat in inputs]

#inputs = [[feat[featureNames.index('sepal length (cm)')], feat[featureNames.index('sepal width (cm)')], feat[featureNames.index('petal length (cm)')], feat[featureNames.index('petal width (cm)')]] for feat in inputs]


#data separation train and test 
import numpy as np

# split data into train and test subsets
np.random.seed(0)
indexes = [i for i in range(len(inputs))]
trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
testSample = [i for i in indexes  if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]
testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]

# feature1train = [ex[0] for ex in trainInputs]
# feature2train = [ex[1] for ex in trainInputs]
# feature3train = [ex[2] for ex in trainInputs]
# feature4train = [ex[3] for ex in trainInputs]
# feature1test = [ex[0] for ex in testInputs]
# feature2test = [ex[1] for ex in testInputs]  
# feature3test = [ex[2] for ex in testInputs]  
# feature4test = [ex[3] for ex in testInputs]  

#tool
from sklearn import linear_model
classifier = linear_model.LogisticRegression(max_iter=2000)
classifier.fit(trainInputs, trainOutputs)

computedTestOutputs = classifier.predict(testInputs)
print(computedTestOutputs)
print("acc score (the tool one): ", classifier.score(testInputs, testOutputs))

from sklearn.metrics import accuracy_score
error = 1 - accuracy_score(testOutputs, computedTestOutputs)
print("classification error (tool): ", error)

# #manual (without sklearn)
# from MyLogisticRegression import MyLogisticRegression
# classifier=MyLogisticRegression()
# classifier.fit(trainInputs,trainOutputs)

# w0, w1, w2, w3, w4 = classifier.intercept_, classifier.coef_[0], classifier.coef_[1], classifier.coef_[2], classifier.coef_[3]
# print('classification model: y(feat1, feat2, feat3, feat4) = ', w0, ' + ', w1, '*feat1 + ', w2, '*feat2 +', w3, '*feat3 + ', w4,'*feat4')

# computedTestOutputs = classifier.predict(testInputs)
# print(testOutputs)
# print(computedTestOutputs)
# from utils import rootMeanSquareError,meanAbsoluteError
# print("acc score (manual): ", 1-meanAbsoluteError(testOutputs,computedTestOutputs))
# print("classification error (manual): ",meanAbsoluteError(testOutputs,computedTestOutputs))


