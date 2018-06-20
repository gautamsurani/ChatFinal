package com.nazpowerchat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nazpowerchat.R;
import com.nazpowerchat.interfaces.OnUserGroupItemClick;
import com.nazpowerchat.models.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mayank on 7/5/17.
 */

public class MenuUsersRecyclerAdapter extends RecyclerView.Adapter<MenuUsersRecyclerAdapter.BaseViewHolder> implements Filterable {
    private Context context;
    private OnUserGroupItemClick itemClickListener;
    private ArrayList<User> dataList, dataListFiltered;
    private Filter filter;

    public MenuUsersRecyclerAdapter(@NonNull Context context, @Nullable ArrayList<User> users) {
        if (context instanceof OnUserGroupItemClick) {
            this.itemClickListener = (OnUserGroupItemClick) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnUserGroupItemClick");
        }

        this.context = context;
        this.dataList = users;
        this.dataListFiltered = users;
        this.filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User row : dataList) {
                        String toCheckWith = row.getNameInPhone() != null ? row.getNameInPhone() : row.getName();
                        if (toCheckWith.toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_user, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof UsersViewHolder) {
            ((UsersViewHolder) holder).setData(dataListFiltered.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return this.filter;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    class UsersViewHolder extends BaseViewHolder {
        @BindView(R.id.user_image)
        CircleImageView userImage;
        @BindView(R.id.user_name)
        TextView userName;

        public UsersViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    itemClickListener.OnUserClick(dataListFiltered.get(pos), pos, userImage);
                }
            });
        }

        public void setData(User user) {
            userName.setText(TextUtils.isEmpty(user.getNameInPhone()) ? user.getName() : user.getNameInPhone());
            String profileImageUrl = user.getImage();
            Glide.with(context).load(profileImageUrl).apply(new RequestOptions().placeholder(R.drawable.yoohoo_placeholder)).into(userImage);

            userName.setCompoundDrawablesWithIntrinsicBounds(0, 0, user.isOnline() ? R.drawable.ring_green : 0, 0);
        }
    }
}
