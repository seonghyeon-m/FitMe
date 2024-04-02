import React from 'react';
import { Link } from 'react-router-dom';
import { TbThumbUp } from 'react-icons/tb';

type ImageType = {
  id: number;
  url: string;
};

type BrandType = {
  id: number;
  name: string;
};

type ItemType = {
  id: number;
  name: string;
  price: number;
  mainImages: ImageType[];
  brand: BrandType;
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
};

export default function Item({
  id,
  name,
  price,
  mainImages,
  brand,
  likeCount,
  reviewRating,
  reviewCount,
}: ItemType) {
  return (
    <>
      <div className='flex-col w-[31.5%] mx-[0.916%] mb-2'>
        <Link to={`/detail/${id}`}>
          <div>
            {mainImages[0] ? (
              <img
                src={mainImages[0].url}
                alt='main_image'
                className='aspect-[3/4] object-cover rounded-lg'
              />
            ) : (
              <div className='bg-gray-400 aspect-[3/4] object-cover rounded-lg' />
            )}
          </div>
          <div className='mt-1 justify-start text-start'>
            <p className='text-xs'>{brand.name}</p>
            <p className='text-xs truncate'>{name}</p>
            <p className='font-bold'>{price.toLocaleString()}원</p>
            <div className='flex justify-between'>
              <p className='text-xs flex flex-row items-baseline'>
                <TbThumbUp className='font-light' />
                {likeCount}
              </p>
              <p className='text-xs'>
                <span className='text-yellow-300'>★</span> {reviewRating.toFixed(1)} (
                {reviewCount.toLocaleString()}개)
              </p>
            </div>
          </div>
        </Link>
      </div>
    </>
  );
}
