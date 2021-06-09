class MyBGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    #batch GD
    def fit(self, x, y, learningRate = 0.001, noEpochs = 1000):
        self.coef_ = [0.0 for _ in range(len(x[0]) + 1)]    #beta or w coefficients y = w0 + w1 * x1 + w2 * x2 + ...
        for epoch in range(noEpochs):
            for i in range(len(x)): # for each sample from the training data
                ycomputed = self.eval(x[i])     # estimate the output
            crtError = ycomputed - y[i]     # compute the error for the current sample
            for j in range(0, len(x[0])):   # update the coefficients in batch style (at the end of each epoch) 
                self.coef_[j] = self.coef_[j] - learningRate * crtError * x[i][j]
            self.coef_[len(x[0])] = self.coef_[len(x[0])] - learningRate * crtError

        self.intercept_ = self.coef_[-1]
        self.coef_ = self.coef_[:-1]

    def eval(self, x):
        y = self.coef_[-1]
        for j in range(len(x)):
            y += self.coef_[j] * x[j]
        return y 

    def predict(self, x):
        yComputed = [self.eval(xi) for xi in x]
        return yComputed