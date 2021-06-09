from Node import MyNode
from math import exp
from utils import index_max_term

def sigmoid(x):
	return 1.0 / (1.0 + exp(-x))

def tanh(x):
	return (exp(x) - exp(-x)) / (exp(x) + exp(-x))

class MyNetwork:
    def __init__(self,train_in, train_out, test_in, test_out,no_epochs):
        self.network = []
        self.train_in=train_in
        self.train_out=train_out
        self.test_in=test_in
        self.test_out=test_out  
        self.no_classes=len(set(test_out))
        self.no_epochs=no_epochs

    def initializare_layer_intrare(self,no_intrari,no_weights):
        layer_intrare=[]
        for _ in range(0,no_intrari):
            layer_intrare.append(MyNode(no_weights))
        self.network.append(layer_intrare)

    def initializare_layer_ascuns(self,no_nodes,no_weights):
        layer_ascuns=[]
        for _ in range(0,no_nodes):
            layer_ascuns.append(MyNode(no_weights))
        self.network.append(layer_ascuns)

    def initializare_layer_iesire(self,no_weights):
        layer_iesire=[]
        for _ in range(0,self.no_classes):
            layer_iesire.append(MyNode(no_weights))
        self.network.append(layer_iesire)

    def activare_cu_funcTANH(self,no_weights,weights,in_values):
        res=0
        for i in range(no_weights):
            if(i==0):
                res+=weights[i]
            else:
                res+=weights[i] * in_values[i-1]
        return tanh(res)

    def get_computed_values(self, in_values):
        comp_values=in_values
        for layer in self.network:
            current_comp_values=[]
            for node in layer:
                current_comp_values.append(self.activare_cu_funcTANH(node.no_weights,node.weights,comp_values))
            comp_values=current_comp_values
        return comp_values
    
    def update_weights(self,input_values,learning_rate=0.0000001):
        for layerIndex in range(0,len(self.network)):
            if(layerIndex>0):
                input_values=[node.res for node in self.network[layerIndex-1]]
            for node in self.network[layerIndex]:
                node.weights[0]+=node.loss*learning_rate
                for i in range(1,len(input_values)):
                    node.weights[i]+=learning_rate*node.loss*input_values[i-1]

    def backPropagation(self,real):
        for layerIndex in reversed(range(len(self.network))):
            losses=[]
            if layerIndex == len(self.network)-1:
                 for nodeIndex in range(len(self.network[layerIndex])):
                    losses.append(real[nodeIndex]-self.network[layerIndex][nodeIndex].res)
            else:
                 for nodeIndex in range(len(self.network[layerIndex])):
                    loss=0.0
                    for node in self.network[layerIndex+1]:
                        loss+=(node.weights[nodeIndex+1] * node.loss)
                    losses.append(loss)
            for nodeIndex in range(len(self.network[layerIndex])): 
                node=self.network[layerIndex][nodeIndex]
                node.loss=losses[nodeIndex]*node.res*(1.0-node.res)

    def fit(self):
        for _ in range(self.no_epochs):
            for train_input, train_output in zip(self.train_in,self.train_out):
                self.get_computed_values(train_input)
                real=[0]*self.no_classes
                real[train_output]+=1
                self.backPropagation(real)
            self.update_weights(train_input)
    
    def predict_sample(self,test):
        return index_max_term(self.get_computed_values(test))

    def predict(self):
        return [self.predict_sample(test) for test in self.test_in]
    
    def accuracy(self,computedValues):
        correct_classified=[]
        sum_of_correct_classif=0
        for i in range(len(self.test_out)):
            if(self.test_out[i]==computedValues[i]):
                correct_classified.append(1)
                sum_of_correct_classif+=1
            else:
                correct_classified.append(0)
        return sum_of_correct_classif/len(self.test_out)