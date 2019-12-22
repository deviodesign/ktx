@file:Suppress("NOTHING_TO_INLINE", "LoopToCallChain")

package ktx.collections

import com.badlogic.gdx.utils.Pool

/** Alias for [com.badlogic.gdx.utils.Array] avoiding name collision with the standard library. */
typealias GdxArray<Element> = com.badlogic.gdx.utils.Array<Element>

/** Alias for [com.badlogic.gdx.utils.BooleanArray] avoiding name collision with the standard library. */
typealias GdxBooleanArray = com.badlogic.gdx.utils.BooleanArray

/** Alias for [com.badlogic.gdx.utils.FloatArray] avoiding name collision with the standard library. */
typealias GdxFloatArray = com.badlogic.gdx.utils.FloatArray

/** Alias for [com.badlogic.gdx.utils.IntArray] avoiding name collision with the standard library. */
typealias GdxIntArray = com.badlogic.gdx.utils.IntArray

/** Alias for [com.badlogic.gdx.utils.CharArray] avoiding name collision with the standard library. */
typealias GdxCharArray = com.badlogic.gdx.utils.CharArray

/** Alias for [com.badlogic.gdx.utils.LongArray] avoiding name collision with the standard library. */
typealias GdxLongArray = com.badlogic.gdx.utils.LongArray

/** Alias for [com.badlogic.gdx.utils.ShortArray] avoiding name collision with the standard library. */
typealias GdxShortArray = com.badlogic.gdx.utils.ShortArray

/**
 * Default LibGDX array size used by most constructors.
 */
const val defaultArraySize = 16

/**
 * Returns the last valid index for the array. -1 if the array is empty.
 */
inline val <Type> GdxArray<Type>?.lastIndex: Int
  get() = size() - 1

/**
 * Returns the last valid index for the array. -1 if the array is empty.
 */
inline val GdxIntArray?.lastIndex: Int
  get() = size() - 1

/**
 * Returns the last valid index for the array. -1 if the array is empty.
 */
inline val GdxFloatArray?.lastIndex: Int
  get() = size() - 1

/**
 * Returns the last valid index for the array. -1 if the array is empty.
 */
inline val GdxBooleanArray?.lastIndex: Int
  get() = size() - 1

/**
 * @param ordered if false, methods that remove elements may change the order of other elements in the array,
 *      which avoids a memory copy.
 * @param initialCapacity initial size of the backing array.
 * @return a new instance of [Array].
 */
inline fun <reified Type : Any> gdxArrayOf(ordered: Boolean = true, initialCapacity: Int = defaultArraySize): GdxArray<Type> =
    GdxArray(ordered, initialCapacity, Type::class.java)

/**
 * @param elements will be initially stored in the array.
 * @return a new instance of [Array].
 */
inline fun <Type : Any> gdxArrayOf(vararg elements: Type): GdxArray<Type> = GdxArray(elements)

/**
 * A method wrapper over [Array.size] variable compatible with nullable types.
 * @return current amount of elements in the array.
 */
inline fun <Type> GdxArray<Type>?.size(): Int = this?.size ?: 0

/**
 * A method wrapper over [IntArray.size] variable compatible with nullable types.
 * @return current amount of elements in the array.
 */
inline fun GdxIntArray?.size(): Int = this?.size ?: 0

/**
 * A method wrapper over [FloatArray.size] variable compatible with nullable types.
 * @return current amount of elements in the array.
 */
inline fun GdxFloatArray?.size(): Int = this?.size ?: 0

/**
 * A method wrapper over [BooleanArray.size] variable compatible with nullable types.
 * @return current amount of elements in the array.
 */
inline fun GdxBooleanArray?.size(): Int = this?.size ?: 0

/**
 * @return true if the array is null or has no elements.
 */
inline fun <Type> GdxArray<Type>?.isEmpty(): Boolean = this == null || this.size == 0

/**
 * @return true if the array is not null and contains at least one element.
 */
inline fun <Type> GdxArray<Type>?.isNotEmpty(): Boolean = this != null && this.size > 0

/**
 * @param index index of the element in the array.
 * @param alternative returned if index is out of bounds or the element is null.
 * @return a non-null value of stored element or the alternative.
 */
operator fun <Type> GdxArray<Type>.get(index: Int, alternative: Type): Type {
  if (index >= this.size) return alternative
  return this[index] ?: alternative
}

/**
 * @param elements will be iterated over and added to the array.
 */
fun <Type> GdxArray<Type>.addAll(elements: Iterable<Type>) =
    elements.forEach { this.add(it) }

/**
 * @param elements will be iterated over and removed from the array.
 * @param identity if true, values will be compared by references. If false, equals method will be invoked.
 */
fun <Type> GdxArray<Type>.removeAll(elements: Iterable<Type>, identity: Boolean = false) =
    elements.forEach { this.removeValue(it, identity) }

/**
 * @param elements will be iterated over and removed from the array.
 * @param identity if true, values will be compared by references. If false, equals method will be invoked.
 */
fun <Type> GdxArray<Type>.removeAll(elements: Array<out Type>, identity: Boolean = false) =
    elements.forEach { this.removeValue(it, identity) }

/**
 * Allows to append elements to arrays with pleasant, chainable `array + element0 + element1` syntax.
 * @param element will be added to the array.
 * @return this array.
 */
operator fun <Type> GdxArray<Type>.plus(element: Type): GdxArray<Type> {
  this.add(element)
  return this
}

/**
 * Allows to quickly addAll all elements of another iterable to this array with a pleasant, chainable operator syntax.
 * @param elements will be added to the array.
 * @return this array.
 */
operator fun <Type> GdxArray<Type>.plus(elements: Iterable<Type>): GdxArray<Type> {
  this.addAll(elements)
  return this
}

/**
 * Allows to quickly addAll all elements of a native array to this array with a pleasant, chainable operator syntax.
 * @param elements will be added to the array.
 * @return this array.
 */
operator fun <Type> GdxArray<Type>.plus(elements: Array<out Type>): GdxArray<Type> {
  this.addAll(elements, 0, elements.size)
  return this
}

/**
 * Allows to remove elements from arrays with pleasant, chainable `array - element0 - element1` syntax.
 * @param element will be removed from the array.
 * @return this array.
 */
operator fun <Type> GdxArray<Type>.minus(element: Type): GdxArray<Type> {
  this.removeValue(element, false)
  return this
}

/**
 * Allows to quickly remove all elements of another iterable from this array with a pleasant, chainable operator syntax.
 * @param elements will be removed from the array.
 * @return this array.
 */
operator fun <Type> GdxArray<Type>.minus(elements: Iterable<Type>): GdxArray<Type> {
  this.removeAll(elements)
  return this
}

/**
 * Allows to quickly remove all elements of a native array from this array with a pleasant, chainable operator syntax.
 * @param elements will be removed from the array.
 * @return this array.
 */
operator fun <Type> GdxArray<Type>.minus(elements: Array<out Type>): GdxArray<Type> {
  this.removeAll(elements)
  return this
}

/**
 * Allows to check if an array contains an element using the "in" operator.
 * @param element might be in the array.
 * @return true if the element is equal to any value stored in the array.
 */
operator fun <Type> GdxArray<Type>.contains(element: Type): Boolean = this.contains(element, false)

/**
 * Allows to iterate over the array with access to [MutableIterator], which allows to remove elements from the collection
 * during iteration.
 * @param action will be invoked for each array element. Allows to remove elements during iteration. The first function
 *      argument is the element from the array, the second is the array iterator. The iterator is guaranteed to be the
 *      same instance during one iteration.
 */
inline fun <Type> GdxArray<Type>.iterate(action: (Type, MutableIterator<Type>) -> Unit) {
  val iterator = iterator()
  while (iterator.hasNext()) action.invoke(iterator.next(), iterator)
}

/**
 * Sorts elements in the array in-place descending according to their natural sort order.
 */
fun <Type : Comparable<Type>> GdxArray<out Type>.sortDescending() {
  this.sort(reverseOrder())
}

/**
 * Sorts elements in the array in-place according to natural sort order of the value returned by specified [selector] function.
 */
inline fun <Type, R : Comparable<R>> GdxArray<out Type>.sortBy(crossinline selector: (Type) -> R?) {
  if (size > 1) this.sort(compareBy(selector))
}

/**
 * Sorts elements in the array in-place descending according to natural sort order of the value returned by specified [selector] function.
 */
inline fun <Type, R : Comparable<R>> GdxArray<out Type>.sortByDescending(crossinline selector: (Type) -> R?) {
  if (size > 1) this.sort(compareByDescending(selector))
}

/**
 * Removes elements from the array that satisfy the [predicate].
 * @param pool Removed items are freed to this pool.
 */
inline fun <Type> GdxArray<Type>.removeAll(pool: Pool<Type>?, predicate: (Type) -> Boolean) {
  var currentWriteIndex = 0
  for (i in 0 until size) {
    val value = items[i]
    if (!predicate(value)) {
      if (currentWriteIndex != i) {
        items[currentWriteIndex] = value
      }
      currentWriteIndex++
    } else {
      pool?.free(value)
    }
  }
  truncate(currentWriteIndex)
}

/**
 * Removes elements from the array that do not satisfy the [predicate].
 * @param pool Removed items are freed to this optional pool.
 */
inline fun <Type> GdxArray<Type>.retainAll(pool: Pool<Type>? = null, predicate: (Type) -> Boolean) {
  var currentWriteIndex = 0
  for (i in 0 until size) {
    val value = items[i]
    if (predicate(value)) {
      if (currentWriteIndex != i) {
        items[currentWriteIndex] = value
      }
      currentWriteIndex++
    } else {
      pool?.free(value)
    }
  }
  truncate(currentWriteIndex)
}

/**
 * Returns a [GdxArray] containing the results of applying the given [transform] function
 * to each element in the original [GdxArray].
 */
inline fun <Type, R> GdxArray<Type>.map(transform: (Type) -> R): GdxArray<R> {
  val destination = GdxArray<R>(this.size)
  for (item in this) {
    destination.add(transform(item))
  }
  return destination
}

/**
 * Returns a [GdxArray] containing only elements matching the given [predicate].
 */
inline fun <Type> GdxArray<Type>.filter(predicate: (Type) -> Boolean): GdxArray<Type> {
  val destination = GdxArray<Type>()
  for (item in this) {
    if (predicate(item)) {
      destination.add(item)
    }
  }
  return destination
}

/**
 * Returns a single [GdxArray] of all elements from all collections in the given [GdxArray].
 */
inline fun <Type, C : Iterable<Type>> GdxArray<out C>.flatten(): GdxArray<Type> {
  val destination = GdxArray<Type>()
  for (item in this) {
    destination.addAll(item)
  }
  return destination
}

/**
 * Returns a single [GdxArray] of all elements yielded from results of transform function being invoked
 * on each entry of original [GdxArray].
 */
inline fun <Type, R> GdxArray<Type>.flatMap(transform: (Type) -> Iterable<R>): GdxArray<R> {
  return this.map(transform).flatten()
}

/**
 * @param initialCapacity initial capacity of the set. Will be resized if necessary. Defaults to array size.
 * @param loadFactor decides how many elements the set might contain in relation to its total capacity before it is resized.
 * @return values copied from this array stored in a LibGDX set.
 */
fun <Type : Any> GdxArray<Type>.toGdxSet(initialCapacity: Int = this.size, loadFactor: Float = defaultLoadFactor):
    GdxSet<Type> {
  val set = GdxSet<Type>(initialCapacity, loadFactor)
  set.addAll(this)
  return set
}

/**
 * @param ordered if false, methods that remove elements may change the order of other elements in the array,
 *      which avoids a memory copy.
 * @param initialCapacity initial size of the backing array.
 * @return values copied from this iterable stored in a LibGDX array.
 */
inline fun <reified Type : Any> Iterable<Type>.toGdxArray(ordered: Boolean = true, initialCapacity: Int = defaultArraySize):
    GdxArray<Type> {
  val array = GdxArray<Type>(ordered, initialCapacity, Type::class.java)
  array.addAll(this)
  return array
}

/**
 * @param ordered if false, methods that remove elements may change the order of other elements in the array,
 *      which avoids a memory copy.
 * @param initialCapacity initial size of the backing array. Defaults to this array size.
 * @return values copied from this array stored in a LibGDX array.
 */
inline fun <reified Type : Any> Array<Type>.toGdxArray(ordered: Boolean = true, initialCapacity: Int = this.size):
    GdxArray<Type> {
  val array = GdxArray<Type>(ordered, initialCapacity, Type::class.java)
  array.addAll(this, 0, this.size)
  return array
}

/**
 * @param ordered if false, methods that remove elements may change the order of other elements in the array,
 *      which avoids a memory copy.
 * @param initialCapacity initial size of the backing array. Defaults to this array size.
 * @return values copied from this array stored in an optimized LibGDX int array.
 */
fun IntArray.toGdxArray(ordered: Boolean = true, initialCapacity: Int = this.size): GdxIntArray {
  val array = GdxIntArray(ordered, initialCapacity)
  array.addAll(this, 0, this.size)
  return array
}

/**
 * @param ordered if false, methods that remove elements may change the order of other elements in the array,
 *      which avoids a memory copy.
 * @param initialCapacity initial size of the backing array. Defaults to this array size.
 * @return values copied from this array stored in an optimized LibGDX float array.
 */
fun FloatArray.toGdxArray(ordered: Boolean = true, initialCapacity: Int = this.size): GdxFloatArray {
  val array = GdxFloatArray(ordered, initialCapacity)
  array.addAll(this, 0, this.size)
  return array
}

/**
 * @param ordered if false, methods that remove elements may change the order of other elements in the array,
 *      which avoids a memory copy.
 * @param initialCapacity initial size of the backing array. Defaults to this array size.
 * @return values copied from this array stored in an optimized LibGDX boolean array.
 */
fun BooleanArray.toGdxArray(ordered: Boolean = true, initialCapacity: Int = this.size): GdxBooleanArray {
  val array = GdxBooleanArray(ordered, initialCapacity)
  array.addAll(this, 0, this.size)
  return array
}
