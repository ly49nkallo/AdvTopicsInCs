import copy

class Node:
    def __init__(self, state:list = list(), parentNode = None, numOfTowers:int = 3, numOfDisks:int = 3):
        self.state = state
        self.parentNode = parentNode
        self.numOfDisks = numOfDisks
        self.numOfTowers = numOfTowers
        if state == list():
            self.state = self.constructStartingState()
        assert(len(self.state) == numOfTowers)
        for tower in self.state: assert(len(tower) == numOfDisks)
        # assert isinstance(self.index, int)
    
    def __repr__(self):
        return " state: " + repr(self.state)

    def isValidState(self) -> bool: pass # trust in my own coding abilities to not need this

    def getNeighbors(self) -> set:
        # structure of state:
        # [[Tower1],[Tower2],[Tower3]]
        # TowerA = [Disk in slot 1, Disk in slot 2, Disk in slot 3]

        possibleNeighbors = set()  # check out my custom hash function !
        for fromTower in range(len(self.state)):
            disk = self.getTopDisk(fromTower)
            if disk == None: continue
            for toTower in range(len(self.state)):
                if fromTower == toTower: continue
                newNode = Node(state = copy.deepcopy(self.state), parentNode = self,  # super strange multi-layer pass by reference errors
                                numOfDisks=self.numOfDisks, numOfTowers=self.numOfTowers) 
                newNode.removeTopDisk(fromTower)
                if newNode.placeDisk(toTower, disk):
                    possibleNeighbors.add(newNode)
        return possibleNeighbors

    def getTopDisk(self, tower_index: int) -> int:
        try:
            return min([disk for disk in self.state[tower_index] if disk])
        except ValueError:
            return None

    def removeTopDisk(self,tower_index:int):
        if self.getTopDisk(tower_index) == None:
            return False
        self.state[tower_index][self.state[tower_index].index(self.getTopDisk(tower_index))] = 0
        return True
    
    def placeDisk(self,tower_index:int,disk:int) -> bool:
        if disk == 0: return False
        tower = self.state[tower_index]
        for i in range(tower.__len__()):
            if tower[i] == 0:
                tower[i] = disk
                return True
            if tower[i] <= disk:
                return False
        return False

    def isTerminalNode(self) -> bool: 
        # return self.state == [[0, 0, 0, 0], [0, 0, 0, 0], [4, 3, 2, 1]]
        for tower in range(len(self.state)):
            if tower == len(self.state) - 1:
                return self.state[-1] == list(reversed(list(range(1, len(self.state[tower]) + 1))))
            if self.state[tower] != [0 for i in range(len(self.state[tower]))]: return False
        return True

    def constructStartingState(self) -> list:
        state = list()
        for tower in range(self.numOfTowers):
            if tower == 0: 
                state.append(list(reversed(list(range(1,self.numOfDisks + 1)))))
            else:
                state.append([0 for i in range(self.numOfDisks)])
        return state

    def __eq__(self, other) -> bool:
        try:
            return other.state == self.state
        except AttributeError:
            return False

    def __hash__(self):
        hash = 0
        n = 0
        # even more list comprehension :)
        flat_list = [item for sublist in self.state for item in sublist]
        for item in flat_list:
            hash += item * (self.numOfDisks ** n)
            n += 1
        return hash
    
    def __len__(self):
        return len(self.state)