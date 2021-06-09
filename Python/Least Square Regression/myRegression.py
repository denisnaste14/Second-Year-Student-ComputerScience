import numpy as np
from math import exp
from math import log2
from numpy.linalg import inv
 
class MyLinearUnivariateRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = 0.0
    # W = (x^t * X)^-1 X^t*Y
    def fit(self, x, y):
        x=np.array(x)
        y=np.array(y)

        xTransposed=x.transpose()
        
        xOriTransposed=np.dot(x,xTransposed)

        inversa=inv(xOriTransposed)

        inversaOriTransp=np.dot(xTransposed,inversa)

        return np.dot(inversaOriTransp,y)
        
        
    # predict the outputs for some new inputs (by using the learnt model)
    def predict(self, x):
        if (isinstance(x[0], list)):
            return [self.intercept_ + self.coef_ * val[0] for val in x]
        else:
            return [self.intercept_ + self.coef_ * val for val in x]