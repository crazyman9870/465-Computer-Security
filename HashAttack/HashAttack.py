import sys
import string
import random
from random import randint


def stringGenerator(size=6, chars=string.ascii_uppercase + string.ascii_lowercase + string.digits):
	return ''.join(random.choice(chars) for x in range(size))

def numberGenerator(size=6, chars=string.digits):
	return int(''.join(random.choice(chars) for x in range(size)))

def main():
	for i in range(10):
		bits = randint(0,9999) & 0xF
		#bits = hex(randint(0,9999)) & 0xF #4 bits
		#bits = stringGenerator() & 0xFF #8 bits
		#bits = stringGenerator() & 0xFFF #12 bits
		#bits = stringGenerator() & 0xFFFF #16 bits
		#bits = stringGenerator() & 0xFFFFF #20 bits
		print(sys.getsizeof(bits))

	print('Finished')

if __name__ == '__main__':
	main()