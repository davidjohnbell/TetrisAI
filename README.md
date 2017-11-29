# TetrisAI
QUESTION: How do you solve a problem that you can't brute force?

ANSWER: Teach the computer to develop a "good enough" solution over many attempts. 

A Genetic Algorithm (GA) is a hueristic inspired by natural selection. Darwin theorized that individuals compete for limited resources and the most adept individuals are most likely to pass on their genes. Over many generations the characteristics that make an individual particularely adept get strenghtened and become more common throughout the population. However, every once in awhile a mutation occurs and a new, more remarkable, individual emerges.

Inspired by a YouTube video I saw from creator Siraj Rivaal I set out to make a sandbox environment where I, and hopefully others, could learn more about Genetic Algorithms. In this sandbox, individuals compete for the highest Tetris score in order to live on. Each indivual is refered to as a genome, which is homage to the gene responsible for the ability to play tetris. The gene's ability to play Tetris depends on the chromosomes, which is homage to the particular encoding of genetic information. Over several generations the genomes cross and mutate, slowly developing the ability to play Tetris.

## Concepts
Population - the set of genomes

Selection - the process of picking which individuals make it to the next generation

Cross Over - the process of taking two genomes, mom and dad, and constructing a new child genome

Mutation - the encoding of the genome changes in some way

## Structure
* src
  * environment
    * Environment
    * IPopulationManager
  * game
  * genomes
    * AbstractGenome
  * statistics
    * IReporter
  * tests
    * simulations
    * tetris
