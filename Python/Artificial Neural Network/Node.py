from random import randint
import numpy as np


class MyNode:
    def __init__(self, no_weights):
        self.no_weights=no_weights
        self.weights=[randint(0,1) for _ in range(no_weights)]
        self.loss=0.0
        self.res=0