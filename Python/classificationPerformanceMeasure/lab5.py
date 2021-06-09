realOutputs =     [3, 9.5, 4, 5.1, 6, 7.2, 2, 1]
computedOutputs = [2, 7, 4.5, 6, 3, 8, 3, 1.2]

from predErr import meanAbsoluteError,rootMeanSquareError,averageMAE
print('Mean Absolute Error:',meanAbsoluteError(realOutputs,computedOutputs))
print('Root Mean Square Error:',rootMeanSquareError(realOutputs,computedOutputs))

realMultiTargetOutputs = [[0.5, 1], [-1, 1], [7, -6]]
computedMultiTargetOutputs =[[0, 2], [-1, 2], [8, -5]]
print('Mean Absolute Error for multi-target regression:', averageMAE(realMultiTargetOutputs,computedMultiTargetOutputs))


realLabels=    ['apple','apple','apple','apple','apple','apple','apple','pear', 'pear','pear','pear','pear','pear', 'pear','pear','pear','grape','grape','grape','grape','peach','peach','peach','peach','peach','peach','peach','peach','peach','peach','peach','peach','peach']
computedLabels=['apple','apple','pear', 'apple','apple','apple','peach','apple','pear','pear','pear','pear','pear','apple','pear','pear','grape','grape','grape','pear' ,'peach','apple','peach','apple','peach','pear' ,'apple','peach','peach','peach','apple','peach','apple']

from classificationPerformanceMeasure import my_confusion_matrix,my_accuracy,my_average_precision,my_average_recall
myConfM=my_confusion_matrix(realLabels,computedLabels)
print('My confusion matrix:\n',myConfM)
print('Accuracy:',my_accuracy(len(realLabels),myConfM)) 
print('Precision:',my_average_precision(myConfM)) 
print('Recall:',my_average_recall(myConfM)) 