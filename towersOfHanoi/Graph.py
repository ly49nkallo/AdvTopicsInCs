import Node

class Graph:
    def __init__(self, startingNode, endingNode):
        self.startingNode = startingNode
        self.endingNode = endingNode

    def DFS(self):
        visited = set()
        path = []
        stack = []
        currentNode = self.startingNode
        stack.append(currentNode)
        while len(stack) > 0:
            currentNode = stack.pop()
            if currentNode.isEqual(self.endingNode):
                break
            stack.extend([neighbor for neighbor in currentNode.getNeightbors()])
        

            

