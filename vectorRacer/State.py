import numpy as np
from surfaceTypes import *
from utils import IX, Coord
import functools


class State():
    def __init__(self, track:np.ndarray, trackX:int, trackY:int, positionVec:tuple, velocity:tuple, checkpointClearance:int, cost:float = 0, parent = None, fscore:float=np.infty, gscore:float=np.infty):
        self.track = track
        self.trackX = trackX
        self.trackY = trackY
        self.position = positionVec
        self.velocity = velocity
        self.checkpointClearance = checkpointClearance
        self.cost = cost
        self.parent = parent
        self.fscore = fscore
        self.gscore = gscore
        

    def __hash__(self) -> int:
        return (self.position[0] + self.position[1]*100) // 2 + (self.velocity[0] + self.velocity[1]*100) // 2

    def __repr__(self) -> str:
        if self.parent == None:
            return "Pos: " + repr(self.position) + " | Vel: " + repr(self.velocity) + " | Checkpoint: " + repr(self.checkpointClearance)
        else:
            return "Pos: " + repr(self.position) + " | Vel: " + repr(self.velocity) + " | Checkpoint: " + repr(self.checkpointClearance) + " | Parent: " + repr(self.parent.position) + repr(self.parent.velocity)

    def getNeighboringStates(self) -> set():
        currentSurface = self.track[self.position[1]][self.position[0]]
        possibleMoves = set()
        if currentSurface == WALL:
            raise NameError("Cart in wall")
        elif currentSurface == GRAVEL:
            for i in range(2):
                for j in range(2):
                    if i and j: continue # if both i and j = 1
                    possibleMoves.add((self.velocity[0] + i - 1, self.velocity[1] + j - 1))
        elif currentSurface == CURB:
            possibleMoves.append(self.velocity.copy())
        else:
            for i in range(3):
                for j in range(3):
                    possibleMoves.add((self.velocity[0] + i - 1, self.velocity[1] + j - 1))
        # make sure that the moves do not crash the cart
        for move in possibleMoves.copy():
            newPositionX = self.position[0] + move[0]
            newPositionY = self.position[1] + move[1]
            if newPositionX < 0 or newPositionY < 0:
                possibleMoves.remove(move)
                continue
            if newPositionY >= self.trackY or newPositionX >= self.trackX:
                possibleMoves.remove(move)
                continue
            if self.track[newPositionY][newPositionX] == WALL:
                possibleMoves.remove(move)
            for tile in self.getTrajectory((newPositionX, newPositionY)):
                if self.track[tile[1]][tile[0]] == WALL:
                    try:
                        possibleMoves.remove(move)
                    except KeyError:
                        pass
                    break
        return set([State(self.track, self.trackX, self.trackY, (self.position[0] + move[0], self.position[1] + move[1]), move, self.checkpointClearance, cost=0, parent=self) for move in possibleMoves])

    def checkCheckpointElevation(self):
        currentSurface = [list(i) for i in self.track[self.position[1]-1:self.position[1]+2]]
        currentSurface = [i[self.position[0]-1:self.position[0]+2] for i in currentSurface.copy()]
        currentSurface = [j for sub in currentSurface.copy() for j in sub]
        if self.checkpointClearance + 6 in currentSurface:
            self.checkpointClearance += 1
            return True
        return False

    def isTerminal(self, checkpoint:int) -> bool:
        if self.checkpointClearance < checkpoint:
            return False
        currentSurface = [list(i) for i in self.track[self.position[1]-1:self.position[1]+2]]
        currentSurface = [i[self.position[0]-1:self.position[0]+2] for i in currentSurface.copy()]
        currentSurface = [j for sub in currentSurface.copy() for j in sub]
        if FINISH in currentSurface:
            return True
        return False

    def getTrajectory(self, state2) -> set:
        assert isinstance(self, State) and isinstance(state2, tuple)
        if self == state2: return set()
        trajectory = set()
        # m = dy/dx
        try:
            slope = (self.position[1]-state2[1])/(self.position[0]-state2[0])
        except ZeroDivisionError:
            slope = 1
        yint = self.position[1] - slope*self.position[0]
        if self.position[0] < state2[0]:
            for j in range(self.position[0],state2[0]-1,1):
                trajectory.add((j, round(slope*j + yint)))
        else:
             for j in range(self.position[0],state2[0],-1):
                 
                trajectory.add((j, round(slope*j + yint)))
        return trajectory

    
        


        

    

    
    


    

    
        
