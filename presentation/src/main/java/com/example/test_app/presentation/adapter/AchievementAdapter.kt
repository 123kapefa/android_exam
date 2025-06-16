import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_app.domain.model.PlayerAchievement
import com.example.test_app.presentation.databinding.ItemAchievementBinding

class AchievementAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<PlayerAchievement, AchievementAdapter.VH>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAchievementBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))

    class VH(
        private val binding: ItemAchievementBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayerAchievement) = with(binding) {
            titleTextView.text       = item.title
            descriptionTextView.text = item.description ?: ""
            val url                  = if (item.isUnlocked) item.iconUrl else item.iconGrayUrl
            Glide.with(root).load(url).into(iconImageView)

            root.setOnClickListener { onClick(item.id) }
        }
    }

    private object Diff : DiffUtil.ItemCallback<PlayerAchievement>() {
        override fun areItemsTheSame(o: PlayerAchievement, n: PlayerAchievement) = o.id == n.id
        override fun areContentsTheSame(o: PlayerAchievement, n: PlayerAchievement) = o == n
    }
}