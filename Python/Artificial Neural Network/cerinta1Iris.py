from sklearn import utils
from sklearn.datasets import load_iris
import numpy as np
from myNetwork import MyNetwork

#iris data loading 
data = load_iris()
inputs = data['data']
outputs = data['target']

outputNames = data['target_names']
featureNames = list(data['feature_names'])

# split data into train and test subsets
np.random.seed(1)
indexes = [i for i in range(len(inputs))]
trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
testSample = [i for i in indexes  if not i in trainSample]

trainInputs = [inputs[i] for i in trainSample]
trainOutputs = [outputs[i] for i in trainSample]
testInputs = [inputs[i] for i in testSample]
testOutputs = [outputs[i] for i in testSample]


my_nn_model=MyNetwork(trainInputs,trainOutputs,testInputs,testOutputs,200)
my_nn_model.initializare_layer_intrare(3,len(trainInputs[0]))
my_nn_model.initializare_layer_ascuns(3,len(trainInputs[0]))
my_nn_model.initializare_layer_iesire(len(trainInputs[0]))

my_nn_model.fit()

print("Accuracy:",my_nn_model.accuracy(my_nn_model.predict()))