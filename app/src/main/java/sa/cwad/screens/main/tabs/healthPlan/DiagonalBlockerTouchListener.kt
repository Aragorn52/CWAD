package sa.cwad.screens.main.tabs.healthPlan
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
                initialTouchX = e.x
                initialTouchY = e.y
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val finalTouchX = e.x
                val finalTouchY = e.y
                val deltaX = finalTouchX - initialTouchX
                val deltaY = finalTouchY - initialTouchY

                // Блокируем скроллинг, если движение похоже на диагональное или слишком короткое
                if ((blockDiagonalScrolls && abs(deltaX) < abs(deltaY)) ||
                    (abs(deltaX) < minSwipeDistance && abs(deltaY) < minSwipeDistance)) {
                    return true // Блокируем скроллинг
                }
            }
        }
        return false
    }
}