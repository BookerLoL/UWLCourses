seal :: (Ord a) => [a] -> [a] -> [a]
seal [] ys = ys
seal xs [] = xs 
seal (x:xs) (y:ys) 
    | x < y = x:(seal xs (y:ys))
    | otherwise = y:(seal (x:xs) ys)

isSublist :: [Integer] -> [Integer] -> Bool
isSublist [] [] = True 
isSublist _ [] = False
isSublist [] _ = True
isSublist (x:xs) (y:ys) 
    | x == y && isSublistHelper xs ys = True
    | otherwise = isSublist (x:xs) ys

isSublistHelper :: [Integer] -> [Integer] -> Bool
isSublistHelper [] [] = True 
isSublistHelper _ [] = False
isSublistHelper [] _ = True 
isSublistHelper (x:xs) (y:ys) 
    | x == y = isSublistHelper xs ys
    | otherwise = False


combinator :: [a] -> [a] -> [[a]]
combinator xs xy = [[x, y] | x <- xs, y <- xy]

mightOfPythagorus :: Integer -> [(Integer, Integer, Integer)]
mightOfPythagorus k = [(a,b,c) | c <- [1..k], b <- [1..c], a <- [1..b] , a^2 + b^2 == c^2]  