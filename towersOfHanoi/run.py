from Graph import Graph
from Node import *

def main():
    startingNode = Node(numOfDisks=4,numOfTowers=3) 
    graph = Graph(startingNode)
    for node in graph.BFS():
        print(node)

if __name__ == "__main__":
    main()