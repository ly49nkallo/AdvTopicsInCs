from surfaceTypes import *
from utils import IX, Coord
from Cart import Cart
import numpy as np
import matplotlib.pyplot as plt

class Game:
    def __init__(self, track:np.ndarray, trackX:int, trackY:int):
        assert isinstance(track, np.ndarray)
        self.track = track
        self.trackX = trackX
        self.trackY = trackY
        self.start = Coord(list(self.track.ravel()).index(START), self.trackX)
        print('start:', self.start)
        self.cart = Cart(0, (self.start[0], self.start[1] + 2), self.track, self.trackX, self.trackY)
        print(self.cart)
        print(self.cart.getAvaliableMoves())

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
        if other.start == self.start and other.track == self.track:
            return True
        return False
    
    
