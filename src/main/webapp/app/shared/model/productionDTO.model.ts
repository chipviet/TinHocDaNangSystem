import { Moment } from 'moment';
import { IBrand } from 'app/shared/model/brand.model';
import { ICategory } from 'app/shared/model/category.model';
import { ICartProduction } from 'app/shared/model/cart-production.model';

export interface IProductionDTO {
  id?: number;
  name?: string;
  price?: number;
  description?: string;
  configuration?: string;
  imageURL?: string;
  imageFile?: Array<File>;
  salePrice?: number;
  quantity?: number;
  condition?: number;
  origin?: string;
  creationDate?: string;
  brand?: IBrand;
  category?: ICategory;
  cartProductions?: ICartProduction[];
}

export const defaultValue: Readonly<IProductionDTO> = {};
