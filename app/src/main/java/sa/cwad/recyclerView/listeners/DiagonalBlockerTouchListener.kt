package sa.cwad.recyclerView.listeners
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class DiagonalBlockerTouchListener(
    private val blockDiagonalScrolls: Boolean,
    private val minSwipeDistance: Float
) : RecyclerView.OnItemTouchListener {

    private var initialTouchX = 0f
    private var initialTouchY = 0f

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                // Сохраняем начальную точку касания
                initialTouchX = e.x
                initialTouchY = e.y
                // Останавливаем скроллинг при начале касания
                rv.stopScroll()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // Вычисляем конечную точку касания и направление движения
                val finalTouchX = e.x
                val finalTouchY = e.y
                val deltaX = finalTouchX - initialTouchX
                val deltaY = finalTouchY - initialTouchY

                if (finalTouchX == initialTouchX || finalTouchY == initialTouchY) {
                    return false
                }
                // Определяем, следует ли блокировать скроллинг
                if ((blockDiagonalScrolls && abs(deltaX) < abs(deltaY)) ||
                    (abs(deltaX) < minSwipeDistance && abs(deltaY) < minSwipeDistance)) {
                    // Блокируем скроллинг, но разрешаем дальнейшую обработку события
                    return true // Возвращаем true, чтобы RecyclerView сам обрабатывал касание
                } else {
                    // Если условия для блокировки скроллинга не выполняются, разрешаем дальнейшую обработку события
                    return false
                }
            }
        }
        // По умолчанию разрешаем дальнейшую обработку события
        return false
    }
}