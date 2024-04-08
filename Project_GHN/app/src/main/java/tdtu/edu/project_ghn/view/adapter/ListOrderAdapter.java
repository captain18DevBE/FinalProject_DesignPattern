package tdtu.edu.project_ghn.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.LocalDateTime;
import java.util.List;

import tdtu.edu.project_ghn.R;
import tdtu.edu.project_ghn.model.OrderDTO;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {
    private Context mContext;
    private List<OrderDTO> orderDTOList;

    public ListOrderAdapter(Context context, List<OrderDTO> orderDTOS) {
        this.mContext = context;
        this.orderDTOList = orderDTOS;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView address, dateTime, type, phoneNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.txtAddressListOrder);
            dateTime = itemView.findViewById(R.id.txtDateTimeListOrder);
            type = itemView.findViewById(R.id.txtTypeListOrder);
            phoneNumber = itemView.findViewById(R.id.txtPhoneListOrder);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View orderHolder = inflater.inflate(R.layout.recycle_list_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(orderHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDTO orderDTO = orderDTOList.get(position);

        holder.address.setText(orderDTO.getAddress());
        holder.dateTime.setText(orderDTO.getDateTime().toString());
        holder.type.setText(orderDTO.getTypeOfProduct());
        holder.phoneNumber.setText(orderDTO.getPhoneNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(orderDTO);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(OrderDTO orderDTO);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        if (orderDTOList != null) {
            return orderDTOList.size();
        }
        return 0;
    }



}
