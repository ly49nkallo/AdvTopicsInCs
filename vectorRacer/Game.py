from typing import Any
from surfaceTypes import *
from utils import IX, Coord
import numpy as np
import matplotlib.pyplot as plt
from State import State

class Game:
    def __init__(self, track:np.ndarray, trackX:int, trackY:int):
        assert isinstance(track, np.ndarray)
        self.track = track
        self.trackX = trackX
        self.trackY = trackY
        self.start = Coord(list(self.track.ravel()).index(START), self.trackX)
        print('start:', self.start)
        self.startingVel = (0,0)
        self.maxCheckpoint = max(self.track.ravel()) - 5

    # fun overrides
    def __repr__(self) -> str:
        fig = plt.figure(figsize=(7,5))
        ax = fig.add_subplot(111)
        ax.set_title('colorMap')
        plt.imshow(self.track)
        ax.set_aspect('equal')
        cax = fig.add_axes([0.12, 0.1, 0.78, 0.8])
        cax.get_xaxis().set_visible(False)
        cax.get_yaxis().set_visible(False)
        cax.patch.set_alpha(0)
        cax.set_frame_on(False)
        plt.colorbar(ax = ax, orientation='vertical')
        plt.show()
        return str()
    
    def __eq__(self, other) -> bool:
        try:
            assert isinstance(other, Game)
        except AssertionError:
            raise NameError("Trying to compare a game class to a non game class")
        if other.start == self.start and other.track == self.track:
            return True
        return False
    
    # not optimal and possibly not even feasible
    def BFS(self) -> list:
        visited = set()
        queue = []
        startingState = State(self.track, self.trackX, self.trackY, self.start, self.startingVel, 0)
        queue.append(startingState)
        terminalNodeReached = False
        while len(queue) > 0:
            currentNode = queue[0]
            print(currentNode)
            queue = queue[1:]
            if currentNode not in visited:
                queue.extend([neighbor for neighbor in currentNode.getNeighboringStates()])
                visited.add(currentNode)
            
            if currentNode.isTerminal(self.maxCheckpoint):
                print("terminal node reached!")
                terminalNodeReached = True
                break
        if not terminalNodeReached: return "NO PATH FOUND"
        path = []
        node = currentNode.parent
        path.append(currentNode)
        while node != None:
            path.append(node)
            node = node.parent
        return list(reversed(path))

    # probably the most optimal solution
    def AStar(self) -> list: pass
    
    
