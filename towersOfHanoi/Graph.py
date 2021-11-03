import Node

class Graph:
    def __init__(self, startingNode):
        self.startingNode = startingNode

    def BFS(self):
        visited = set()
        queue = []
        currentNode = self.startingNode
        queue.append(currentNode)
        while len(queue) > 0:
            currentNode = queue[0]
            queue = queue[1:]
            if currentNode not in visited:
                # list comprehension :)
                queue.extend([neighbor for neighbor in currentNode.getNeightbors() if neighbor not in visited])
                visited.add(currentNode)
            if currentNode.isTerminalNode():
                break
        path = []
        node = currentNode.parentNode
        path.append(currentNode)
        while node != None:
            path.append(node)
            node = node.parentNode
        return path

