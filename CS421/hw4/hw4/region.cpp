#include <iostream>
#include <algorithm>
#include <math.h>
#include <set>
#include "region.h"

using namespace std;

int randNum(const int min = 0, const int max = Region::PAGE_DIMENSION);
bool isPointWithinRange(const int p_val, const int p_start, const int p_end);
bool isPointWithinRegion(const Region &, const int x_in, const int y_in);

Region::Region() : Region::Region(randNum(), randNum(),
								  randNum(1, Region::DEFAULT_MAX_SIZE),
								  randNum(1, Region::DEFAULT_MAX_SIZE), NULL, NULL)
{
}

Region::Region(int x, int y, int w, int h) : Region::Region(x, y, w, h, NULL, NULL)
{
}

Region::Region(int x, int y, int width, int height, Region *r1, Region *r2) : x(x), y(y), width(width), height(height), r1(r1), r2(r2)
{
}

int Region::isLeaf() const
{
	if (!r1 && !r2)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

int Region::centerX() const
{
	return x + (width / 2);
}

int Region::centerY() const
{
	return y + (height / 2);
}

int Region::area() const
{
	return width * height;
}

int Region::getX() const
{
	return x;
}

int Region::getY() const
{
	return y;
}

int Region::getWidth() const
{
	return width;
}

int Region::getHeight() const
{
	return height;
}

double Region::distance(Region &rhs)
{
	return sqrt(
		pow(centerX() - rhs.centerX(), 2) + pow(centerY() - rhs.centerY(), 2));
}

bool Region::operator<(Region &rhs)
{
	return area() < rhs.area();
}

Region *Region::operator,(Region &other)
{
	if (&other == NULL)
	{
		return NULL;
	}
	int minX = min(this->x, other.x);
	int minY = min(this->y, other.y);
	int maxWidth = max(this->x + this->width, other.x + other.width) - minX;
	int maxHeight = max(this->y + this->height, other.y + other.height) - minY;
	return new Region(minX, minY, maxWidth, maxHeight, this, &other);
}

Region &Region::operator=(Region &rhs)
{
	if (this != &rhs)
	{
		x = rhs.x;
		y = rhs.y;
		width = rhs.width;
		height = rhs.height;
		r1 = rhs.r1;
		r2 = rhs.r2;
	}
	return *this;
}

Region *Region::operator()(int x_in, int y_in)
{
	if (!isPointWithinRegion(*this, x_in, y_in))
	{
		return NULL;
	}
	Region *minRegion = this;
	Region *minLeft = this->r1;
	Region *minRight = this->r2;
	
	if (minLeft)
	{
		minLeft = (*minLeft)(x_in, y_in);
	}
	if (minRight)
	{
		minRight = (*minRight)(x_in, y_in);
	}

	if (minLeft && minRegion->area() > minLeft->area())
	{
		minRegion = minLeft;
	}
	if (minRight && minRegion->area() > minRight->area())
	{
		minRegion = minRight;
	}
	return minRegion;
}

ostream &operator<<(ostream &os, Region &r)
{
	if (&r == NULL)
	{
		os << "NULL";
	}
	else
	{
		os << "[" << r.x << "," << r.y << "," << r.width << "," << r.height
		   << "]";
	}
	return os;
}

ostream &operator/(ostream &os, Region &r)
{
	if (&r == NULL)
	{
		return os;
	}
	else if (r.isLeaf())
	{
		os << "<rect x=\"" << r.x << "\" y=\"" << r.y << "\" width=\""
		   << r.width << "\" height=\"" << r.height
		   << "\" style=\"fill:blue;stroke:black;stroke-width:.05;fill-opacity:.1;stroke-opacity:.9\"/>";
	}
	else
	{
		os << "<rect x=\"" << r.x << "\" y=\"" << r.y << "\" width=\""
		   << r.width << "\" height=\"" << r.height
		   << "\" style=\"fill:yellow;stroke:black;stroke-width:.05;fill-opacity:.0;stroke-opacity:.5\"/>";
		(os / *(r.r1));
		(os / *(r.r2));
	}
	return os;
}

Region *reduce(set<Region *> &set)
{
	Region *min_r1 = NULL, *min_r2 = NULL, *temp_r1 = NULL, *temp_r2 = NULL;
	std::set<Region *>::iterator outerIter, innerIter;
	double min_distance, tempDist;
	while (set.size() != 1)
	{
		min_distance = std::numeric_limits<double>::max();
		outerIter = set.begin();
		while (outerIter != set.end())
		{
			innerIter = std::next(outerIter);
			temp_r1 = *outerIter;
			while (innerIter != set.end())
			{
				temp_r2 = *innerIter;
				tempDist = temp_r1->distance(*temp_r2);
				if (tempDist < min_distance || tempDist == min_distance && temp_r1->area() + temp_r2->area() < min_r1->area() + min_r2->area())
				{
					min_r1 = temp_r1;
					min_r2 = temp_r2;
					min_distance = tempDist;
				}
				innerIter++;
			}
			outerIter++;
		}
		set.erase(min_r1);
		set.erase(min_r2);
		set.insert((*min_r1, *min_r2));
	}
	return *set.begin();
}

int randNum(const int min, const int max)
{
	return min + (rand() % (max - min + 1));
}

bool isPointWithinRegion(const Region &rhs, const int x_pt, const int y_pt)
{
	return isPointWithinRange(x_pt, rhs.getX(), rhs.getX() + rhs.getWidth()) && isPointWithinRange(y_pt, rhs.getY(),																						   rhs.getY() + rhs.getHeight());
}

bool isPointWithinRange(const int p_val, const int p_start, const int p_end)
{
	return p_start <= p_val && p_val <= p_end;
}
