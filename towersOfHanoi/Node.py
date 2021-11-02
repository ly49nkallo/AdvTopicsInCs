import numpy as np

class Node:
    def __init__(self, index, state, parentNode = None, numOfTowers=3, numOfDisks=3):
        self.index = index
        self.state = state
        self.parentNode = parentNode
        assert isinstance(self.state, np.ndarray)
        assert np.shape(self.state) == (numOfDisks,numOfTowers)
        assert isinstance(self.index, int)
    
    def __repr__(self):
        return "node: " + self.index + " state: " + repr(self.state)

    def isValidState(self) -> bool: pass

    def getNeighbors(self) -> set: pass

    def isTerminalNode(self) -> bool: pass

    def isEqual(self, other) -> bool:
        return other.state == self.state
    