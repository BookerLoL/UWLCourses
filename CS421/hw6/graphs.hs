type Node = Integer
type Edge = (Integer, Integer)
type Graph = [Edge]
type Path = [Node]

insert :: (Ord a) => a -> [a] -> [a]
insert x [] = [x]
insert x (y:ys) 
    | x == y = y:ys
    | x < y = x:y:ys
    | otherwise = y:(insert x ys)

nodes :: Graph -> [Node]
nodes [] = []
nodes ((x, y): xs) = (insert x (insert y (nodes xs)))

adjacents :: Node -> Graph -> [Node]
adjacents n g = [y | (x, y) <- g, n == x]

detach :: Node -> Graph -> Graph
detach n g = [(x, y) | (x, y) <- g, x /= n, y /= n]

pathsHelper :: Node -> Node -> Graph -> [Node] -> Path -> [Path] -> [Path]
pathsHelper v1 v2 _ [] path allPaths
        | v1 == v2 = path:allPaths
        | otherwise = []
pathsHelper v1 v2 g (x:xs) currPath allPaths
        | v1 == v2 = currPath:allPaths
        | otherwise = (pathsHelper x v2 (detach x g) (adjacents x g) (currPath++[x]) allPaths)++(pathsHelper v1 v2 g xs currPath allPaths)

paths :: Node -> Node -> Graph -> [Path]
paths v1 v2 [] = []
paths v1 v2 g 
    | v1 == v2 && not (elem v1 (nodes g)) = []
    | otherwise = (pathsHelper v1 v2 (detach v1 g) (adjacents v1 g) [v1] [])
