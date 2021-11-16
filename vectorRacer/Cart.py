import numpy as np
from surfaceTypes import WALL
from utils import IX, Coord
import functools

class Cart:
    def __init__(self, index, startPosition,  track:np.ndarray, trackX:int, trackY:int):
        self.index = index
        self.position = startPosition
        assert isinstance(self.position, tuple)
        self.velocity = (0,0)
        self.track = track
        self.trackX = trackX
        self.trackY = trackY
    
    def __repr__(self) -> str:
        return "cart at location: " + str(self.position) + '\nvelocity: ' + str(self.velocity)
    
    def getAvaliableMoves(self) -> set():
        possibleMoves = set()
        for i in range(3):
            for j in range(3):
                possibleMoves.add((self.velocity[0] + i - 1, self.velocity[1] + j - 1))
        # make sure that the moves do not crash the cart
        for move in possibleMoves.copy():
            if self.track[self.position[1] + move[1]][self.position[0] + move[0]] == WALL:
                possibleMoves.remove(move)
        return possibleMoves

    
        
