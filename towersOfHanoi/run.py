from Graph import Graph
from Node import *
from argparse import ArgumentParser

def main():
    parser = ArgumentParser(description='Solve towers of hanoi',
                            epilog="Code written by Ty Brennan")
    parser.add_argument('-t', '--towers', help='Number of towers', action='store', default=3)
    parser.add_argument('-d', '--disks', help="Number of disks per tower", action='store', default=3)
    args = parser.parse_args()
    startingNode = Node(numOfDisks=args.towers,numOfTowers=args.disks) 
    graph = Graph(startingNode)
    for node in graph.BFS():
        print(node)

if __name__ == "__main__":
    main()