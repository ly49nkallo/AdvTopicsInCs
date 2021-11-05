import Graph
from Node import *

def main():
    startingNode = Node()
    print(startingNode)
    sneighbors = startingNode.getNeighbors()
    print("gn", sneighbors)
    for i in sneighbors:
        print(i)
        print(i.getNeighbors())
    graph = Graph.Graph(startingNode)
    graph.BFS()

if __name__ == "__main__":
    main()