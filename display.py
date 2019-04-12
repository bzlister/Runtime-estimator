import re
import matplotlib.pyplot as plt

def graph(path):
    raw = re.split('\n', open(path).read())
    x = []
    y = []
    diff = []
    for s in raw:
        x.append(int(s.split(": ")[0]))
        y.append(int(s.split(": ")[1]))
    plt.plot(x,y, 'ro')
    plt.show()