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
            #print("current, visited",currentNode, visited)
            if currentNode not in visited:
                # list comprehension :)
                queue.extend([neighbor for neighbor in currentNode.getNeighbors() if neighbor not in visited])
                #print('q', queue)
                visited.add(currentNode)
                
            if currentNode.isTerminalNode():
                print("terminal node reached!")
                break
        path = []
        node = currentNode.parentNode
        path.append(currentNode)
        while node != None:
            path.append(node)
            node = node.parentNode
        return list(reversed(path))

