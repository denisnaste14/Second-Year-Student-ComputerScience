from chromosome import Chromosome
from random import seed, randint  
from utils import readNet
from utils import cost
from utils import generateARandomPermutation
from GA import GA


seed(1)


# initialise de GA parameters
gaParam = {'popSize' : 30, 'noGen' : 20, 'pc' : 0.8, 'pm' : 0.1}
# problem parameters

# problParam = {'min' : MIN, 'max' : MAX, 'function' : fcEval, 'noDim' : noDim, 'noBits' : 8}

problParam = readNet("medium.txt")
problParam['noDim'] = problParam['noNodes']
print(problParam['noNodes'])

#
calcul = lambda x : cost(x,problParam)
problParam['function']  = calcul


# store the best/average solution of each iteration (for a final plot used to anlyse the GA's convergence)


ga = GA(gaParam, problParam)
ga.initialisation()
ga.evaluation()

minim = float("inf")

for g in range(gaParam['noGen']):
    #logic alg
    # ga.oneGeneration()
    ga.oneGenerationElitism()
    # ga.oneGenerationSteadyState()

    bestChromo = ga.bestChromosome()

    if(minim>bestChromo.fitness):
        minim = bestChromo.fitness
        best_solution=bestChromo
    print('Best solution in generation ' + str(g) + ' is: x = ' + str(bestChromo.repres) + ' f(x) = ' + str(bestChromo.fitness))


print(minim)
print(best_solution.fitness)
print(best_solution.repres)