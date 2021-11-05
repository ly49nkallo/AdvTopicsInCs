import Graph
from Node import *

def main():
    startingNode = Node()
    print(startingNode)
    sneighbors = startingNode.getNeighbors()
    print(sneighbors)
    for i in sneighbors:
        print(i.getNeighbors())
    graph = Graph.Graph(startingNode)
    print(graph.BFS())

if __name__ == "__main__":
    main()