import Graph
import Node

def main():
    startingNode = Node()
    endingNode = Node()
    graph = Graph(startingNode, endingNode)
    graph.DFS()

if __name__ == "__main__":
    main()