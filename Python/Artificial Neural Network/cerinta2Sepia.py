from PIL import Image 
from sklearn import neural_network
from sklearn.metrics import accuracy_score
import numpy as np

def loadImages():
    inputs=[]
    outputs=[0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1] # o sa citesc aleator: o poza fara filtru si una cu sepia

    for i in range(10):
        noFilterImageName="poza"+str(i)+'.jpg'
        noFilterImg=Image.open("poze/"+noFilterImageName,"r")
        noFilterImg_resized=noFilterImg.resize((300,300))
        noFilerImgData=np.average(np.asarray(noFilterImg_resized),axis=(0,1))
        inputs.append(noFilerImgData)

        filterImageName="poza"+str(i+10)+'.jpg'
        filterImg=Image.open("poze/"+filterImageName,"r")
        filterImg_resized=filterImg.resize((300,300))
        filterImgData=np.average(np.asarray(filterImg_resized),axis=(0,1))
        inputs.append(filterImgData)
    return inputs,outputs

def run():
    inputs,outputs=loadImages()
    #split inputs and outputs for train and test

    np.random.seed(2)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
    testSample = [i for i in indexes  if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    neural_network_model=neural_network.MLPClassifier(hidden_layer_sizes=(100,),max_iter=2000,activation="tanh",solver="sgd",learning_rate_init=0.1)

    neural_network_model.fit(trainInputs,trainOutputs)
    computedTestOutputs=neural_network_model.predict(testInputs)
    print("Accuracy:", accuracy_score(testOutputs,computedTestOutputs))
run()


